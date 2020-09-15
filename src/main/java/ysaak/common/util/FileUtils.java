package ysaak.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public final class FileUtils {
    private FileUtils() { /**/ }

    public static FileExecResult execute(List<String> command) throws IOException, InterruptedException {

        final ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitVal = process.waitFor();

        return new FileExecResult(exitVal, output.toString());
    }

    public static class FileExecResult {
        private final int exitVal;
        private final String output;

        public FileExecResult(int exitVal, String output) {
            this.exitVal = exitVal;
            this.output = output;
        }

        public int getExitVal() {
            return exitVal;
        }

        public String getOutput() {
            return output;
        }
    }
}
