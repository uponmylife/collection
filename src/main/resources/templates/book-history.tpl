layout "layout.tpl",
        content: {
                booksMap.each { k, v ->
                    h3 k
//                    m.each { month, list ->
//                        h5 month
//                        list.each { book ->
//                            p book.title
//                        }
//                    }
                }
        }

