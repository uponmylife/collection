layout "layout.tpl",
        content: {
            uncheckedBooks.each { book ->
                a(href: "/book/" + book.id, target: "_blank") {
                    img(src: book.coverUrl, style: "width:80px")
                }
            }
            books.each { season ->
                h3 season.key
                season.value.each { book ->
                    a(href: "/book/" + book.id) {
                        img(src: book.coverUrl, style: "width:80px")
                    }
                }
            }

            p ""
        }
