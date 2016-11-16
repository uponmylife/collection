package geo;

public enum BookFilter {
    unread, toread, read, owned, interested, none, all;

    String getSql() {
        switch (this) {
            case unread:
                return " and read=false";
            case toread:
                return " and to_read=true";
            case read:
                return " and read=true";
            case owned:
                return " and owned=true";
            case interested:
                return " and interested=true";
            case none:
                return " and read=false and to_read=false and owned=false and interested=false";
            case all:
                return "";
        }
        return "";
    }
}
