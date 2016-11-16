layout "layout.tpl",
        content: {
            form(class: "row") {
                div(class: "col-xs-5") {
                    select(id: "filterSelect", class: "form-control", onchange: "reloadPage()") {
                        geo.BookFilter.values().each {
                            yieldUnescaped "<option ${it==filter ? "selected" : ""}>$it</option>"
                        }
                    }
                }
                div(class: "col-xs-4") {
                    select(id: "orderSelect", class: "form-control", onchange: "reloadPage()") {
                        geo.BookOrder.values().each {
                            yieldUnescaped "<option ${it==order ? "selected" : ""}>$it</option>"
                        }
                    }
                }
                div(class: "col-xs-3 text-right") {
                    a(class: "btn btn-link", href: "/book/search", "New")
                }
            }
            table(class: "table table-striped table-condensed") {
                books.each { book ->
                    tr(onclick: "location.href='/book/$book.id'") {
                        td(rowspan: 2) {
                            a(href: book.link, target: "_blank") {
                                img(src: book.coverUrl, style: "width:44px")
                            }
                        }
                        td(colspan: 2) {
                            strong book.title
                        }
                        td book.author
                    }
                    tr {
                        td {
                            small book.publisher
                        }
                        td {
                            small book.publishedAt.format('yyyy.MM')
                        }
                        td {
                            table {
                                tr {
                                    td(width: "20px") {
                                        if (book.read) span(class: "glyphicon glyphicon-heart")
                                    }
                                    td(width: "20px") {
                                        if (book.toRead) span(class: "glyphicon glyphicon-time")
                                    }
                                    td(width: "20px") {
                                        if (book.interested) span(class: "glyphicon glyphicon-eye-open")
                                    }
                                    td(width: "20px") {
                                        if (book.owned) span(class: "glyphicon glyphicon-home")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

script '''
function reloadPage() {
    location.replace("/book?filter=" + $("#filterSelect option:selected").val()
        + "&order=" + $("#orderSelect option:selected").val());
}
'''