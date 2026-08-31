package com.jobNinza.enums;

public enum RemoteType {
    REMOTE("Remote", "Work from anywhere"),
    HYBRID("Hybrid", "Mix of remote and in-office"),
    ON_SITE("On-site", "Work from office"),
    FLEXIBLE("Flexible", "Flexible work arrangement"),
    REMOTE_FIRST("Remote-First", "Remote-first company culture"),
    OFFICE_BASED("Office-Based", "Based in office"),
    PARTIALLY_REMOTE("Partially Remote", "Partially remote work"),
    TEMPORARILY_REMOTE("Temporarily Remote", "Temporarily remote due to special circumstances"),
    NOT_SPECIFIED("Not Specified", "Remote work not specified"),
    UNKNOWN("Unknown", "Unknown remote type");

    private final String displayName;
    private final String description;

    RemoteType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Parse workplace type string from various ATS clients
     */
    public static RemoteType fromString(String value) {
        if (value == null || value.isEmpty()) {
            return NOT_SPECIFIED;
        }

        String normalized = value.trim().toLowerCase().replace("-", " ").replace("_", " ");
        
        // Check for exact matches first
        for (RemoteType type : RemoteType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(value) || 
                type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }

        // Check for partial matches
        if (normalized.contains("remote")) {
            if (normalized.contains("hybrid") || normalized.contains("flex")) {
                return HYBRID;
            }
            if (normalized.contains("first")) {
                return REMOTE_FIRST;
            }
            if (normalized.contains("partial")) {
                return PARTIALLY_REMOTE;
            }
            return REMOTE;
        }
        
        if (normalized.contains("hybrid") || normalized.contains("mix")) {
            return HYBRID;
        }
        
        if (normalized.contains("on-site") || normalized.contains("onsite") || 
            normalized.contains("office") || normalized.contains("in-office") ||
            normalized.contains("in office")) {
            return ON_SITE;
        }
        
        if (normalized.contains("flexible") || normalized.contains("flex")) {
            return FLEXIBLE;
        }

        return NOT_SPECIFIED;
    }

    /**
     * Convert to boolean remote flag
     */
    public boolean isRemote() {
        return this == REMOTE || this == REMOTE_FIRST || this == FLEXIBLE;
    }

    /**
     * Check if position allows remote work
     */
    public boolean allowsRemote() {
        return this == REMOTE || this == REMOTE_FIRST || this == FLEXIBLE || 
               this == PARTIALLY_REMOTE || this == HYBRID;
    }
}