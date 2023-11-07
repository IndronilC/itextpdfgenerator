package com.kanini.pdfgenerator.itextpdfgenerator.common.util.uuidgenerator;

import java.util.UUID;

public class UUIDGeneratorUtil {
    protected UUIDGeneratorUtil() {}

    public static UUID generateNewUUID() {
        return UUID.randomUUID();
    }
}
