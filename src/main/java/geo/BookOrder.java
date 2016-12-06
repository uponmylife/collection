package geo;

public enum  BookOrder {
    rank, published, name, random;

    String getSql() {
        switch (this) {
            case rank:
                return " order by rank";
            case published:
                return " order by published_at desc";
            case name:
                return " order by title";
        }
        return "";
    }
}
