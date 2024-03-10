package org.pwojaczek.fixer.responses;

import org.pwojaczek.fixer.responses.objects.FixerErrorDetails;

public class ResFixerError {
    private boolean success;
    private FixerErrorDetails error;

    public boolean isSuccess() {
        return success;
    }

    public FixerErrorDetails getError() {
        return error;
    }
}
