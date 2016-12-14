layout "layout.tpl",
        content: {
            div(class: "text-right") {
                a(class: "btn btn-link", href: "/book", "go to my library")
            }
            uncheckedBooks.each { book ->
                a(href: "/book/" + book.id, target: "_blank") {
                    img(src: "/api/book/${book.id}/cover", style: "width:80px")
                }
            }
            books.each { season ->
                h3 season.key
                season.value.each { book ->
                    a(href: "/book/" + book.id) {
                        img(src: "/api/book/${book.id}/cover", style: "width:80px")
                    }
                }
            }

            p ""
        }
