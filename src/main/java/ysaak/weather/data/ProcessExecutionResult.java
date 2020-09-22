package ysaak.weather.data;

public final class ProcessExecutionResult {
    private final boolean processExited;
    private final int exitVal;
    private final String output;

    public ProcessExecutionResult(boolean processExited, int exitVal, String output) {
        this.processExited = processExited;
        this.exitVal = exitVal;
        this.output = output;
    }

    public boolean isProcessExited() {
        return processExited;
    }

    public int getExitVal() {
        return exitVal;
    }

    public String getOutput() {
        return output;
    }
}
