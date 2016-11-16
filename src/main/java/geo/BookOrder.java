package geo;

public enum  BookOrder {
    rank, published;

    String getSql() {
        switch (this) {
            case rank:
                return " order by rank";
            case published:
                return " order by published_at desc";
        }
        return "";
    }
}
