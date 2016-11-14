layout "layout.tpl",
        content: {
            form(class: "row") {
                div(class: "col-xs-6") {
                    input(type: "text", class: "form-control", name: "query")
                }
                div(class: "col-xs-3") {
                    button(type: "submit", class: "btn btn-default", "Search")
                }
                div(class: "col-xs-2") {
                    a(class: "btn btn-link", href: "/book", "Back")
                }
            }
            table(class: "table table-hover") {
                books.each { book ->
                    tr {
                        td(rowspan: "2") {
                            a(href: book.link, target: "_blank") {
                                img(src: book.coverUrl, style: "width:50px")
                            }
                        }
                        td book.title
                        td book.author
                        td book.publisher
                        td book.publishedAt.format('yyyy.MM')
                        td(class: "text-right", String.format("%,d", book.price))
                    }
                    tr {
                        td(colspan: "4") {
                            small book.description
                        }
                        td {
                            button(class: "btn", onclick: "addBook(" + book.id + ")", "Add")
                        }
                    }
                }
            }
        }

script '''
function addBook(id) {
    ajaxPostForm("/api/book", {id: id}, function (result) {
        alert(result);
    });
}
'''

