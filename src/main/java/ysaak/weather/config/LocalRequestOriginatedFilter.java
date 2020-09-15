package ysaak.weather.config;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@Component
public class LocalRequestOriginatedFilter extends OncePerRequestFilter {
    private static final Multimap<String, String> FILTERED_PATH_IP;
    static {
        FILTERED_PATH_IP = ArrayListMultimap.create(1, 2);
        FILTERED_PATH_IP.putAll("/api/", Arrays.asList("192.168.1.0/24", "127.0.0.1/8", "::1"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean accessForbidden = false;

        for (String path : FILTERED_PATH_IP.keys()) {
            if (shouldBlockRequest(request, path, FILTERED_PATH_IP.get(path))) {
                accessForbidden = true;
                break;
            }
        }

        if (accessForbidden) {
            response.setStatus(HttpURLConnection.HTTP_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print("{\"message\":\"This ip is forbidden\"}");
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean shouldBlockRequest(HttpServletRequest request, String path, Collection<String> subnets) {
        if (request.getRequestURI().startsWith(path)) {
            for (String subnet : subnets) {
                IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(subnet);
                if (ipAddressMatcher.matches(request)) {
                    return false;
                }
            }

            return true;
        }

        return false;
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
