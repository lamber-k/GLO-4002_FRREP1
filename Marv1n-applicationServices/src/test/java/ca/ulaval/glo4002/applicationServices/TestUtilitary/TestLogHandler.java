package ca.ulaval.glo4002.applicationServices.TestUtilitary;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TestLogHandler extends Handler {
    private List<String> recordsLog;

    public TestLogHandler() {
        recordsLog = new ArrayList<>();
    }

    @Override
    public void publish(LogRecord record) {
        recordsLog.add(record.getMessage());
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

    public String getLogs() {
        return recordsLog.toString();
    }
}
