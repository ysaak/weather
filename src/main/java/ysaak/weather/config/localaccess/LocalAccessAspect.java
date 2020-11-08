package ysaak.weather.config.localaccess;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@Aspect
@Configuration
public class LocalAccessAspect {
    private static final List<String> LOCAL_SUBNETS = Arrays.asList("192.168.1.0/24", "127.0.0.1/8", "::1");

    @Before("@annotation(ysaak.weather.config.localaccess.LocalAccess) && args(request,..)")
    public void before(HttpServletRequest request) {
        if (shouldBlockRequest(request)) {
            throw new RuntimeException("Local access only");
        }
    }

    private boolean shouldBlockRequest(HttpServletRequest request) {
        for (String subnet : LOCAL_SUBNETS) {
            LocalAccessAspect.IpAddressMatcher ipAddressMatcher = new LocalAccessAspect.IpAddressMatcher(subnet);
            if (ipAddressMatcher.matches(request)) {
                return false;
            }
        }

        return true;
    }

    private static final class IpAddressMatcher {
        private final int nMaskBits;
        private final InetAddress requiredAddress;

        public IpAddressMatcher(String ipAddress) {
            if (ipAddress.indexOf(47) > 0) {
                String[] addressAndMask = StringUtils.split(ipAddress, "/");
                ipAddress = addressAndMask[0];
                this.nMaskBits = Integer.parseInt(addressAndMask[1]);
            }
            else {
                this.nMaskBits = -1;
            }

            this.requiredAddress = this.parseAddress(ipAddress);
            Assert.isTrue(this.requiredAddress.getAddress().length * 8 >= this.nMaskBits, String.format("IP address %s is too short for bitmask of length %d", ipAddress, this.nMaskBits));
        }

        public boolean matches(HttpServletRequest request) {
            return this.matches(request.getRemoteAddr());
        }

        public boolean matches(String address) {
            InetAddress remoteAddress = this.parseAddress(address);
            if (!this.requiredAddress.getClass().equals(remoteAddress.getClass())) {
                return false;
            } else if (this.nMaskBits < 0) {
                return remoteAddress.equals(this.requiredAddress);
            } else {
                byte[] remAddr = remoteAddress.getAddress();
                byte[] reqAddr = this.requiredAddress.getAddress();
                int nMaskFullBytes = this.nMaskBits / 8;
                byte finalByte = (byte)('\uff00' >> (this.nMaskBits & 7));

                for(int i = 0; i < nMaskFullBytes; ++i) {
                    if (remAddr[i] != reqAddr[i]) {
                        return false;
                    }
                }

                if (finalByte != 0) {
                    return (remAddr[nMaskFullBytes] & finalByte) == (reqAddr[nMaskFullBytes] & finalByte);
                } else {
                    return true;
                }
            }
        }

        private InetAddress parseAddress(String address) {
            try {
                return InetAddress.getByName(address);
            } catch (UnknownHostException var3) {
                throw new IllegalArgumentException("Failed to parse address" + address, var3);
            }
        }
    }
}
