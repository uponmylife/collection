layout "layout.tpl",
        content: {
            form(action: "/book/submit", method: "post") {
                input(type: "hidden", name: "id", value: "$book.id")
                table(class: "table table-striped table-condensed") {
                    tr {
                        td(rowspan: "4") {
                            a(href: book.link, target: "_blank") {
                                img(src: book.coverUrl, style: "width:100px")
                            }
                        }
                        td(colspan: "3") {
                            strong book.title
                        }
                    }
                    tr {
                        td(colspan: "3") {
                            small book.author
                        }
                    }
                    tr {
                        td {
                            small book.publisher
                        }
                        td {
                            small book.publishedAt.format('yyyy.MM')
                        }
                        td {
                            small book.price
                        }
                    }
                    tr {
                        td(colspan: "3") {
                            small book.description
                        }
                    }
                    tr {
                        td(colspan: "4", class: "form-horizontal") {
                            label(class: "col-xs-offset-1 col-xs-5 checkbox") {
                                yieldUnescaped "<input name=read type=checkbox ${book.read ? "checked" : ""}> read "
                                span(class: "glyphicon glyphicon-heart")
                            }
                            label(class: "col-xs-5 checkbox ") {
                                yieldUnescaped "<input name=toRead type=checkbox ${book.toRead ? "checked" : ""}> to read "
                                span(class: "glyphicon glyphicon-time")
                            }
                        }
                    }
                    tr {
                        td(colspan: "4", class: "form-horizontal") {
                            label(class: "col-xs-offset-1 col-xs-5 checkbox ") {
                                yieldUnescaped "<input name=owned type=checkbox ${book.owned ? "checked" : ""}> owned "
                                span(class: "glyphicon glyphicon-home")
                            }
                            label(class: "col-xs-5 checkbox ") {
                                yieldUnescaped "<input name=interested type=checkbox ${book.interested ? "checked" : ""}> interested "
                                span(class: "glyphicon glyphicon-eye-open")
                            }
                        }
                    }
                    tr {
                        td ""
                        td(colspan: "3") {
                            span "rank "
                            select(name: "rank") {
                                option 1
                                option 2
                                option 3
                                option 4
                                option 5
                            }
                        }
                    }
                    tr {
                        td {
                            button(class: "btn btn-default btn-xs", onclick: "deleteBook(" + book.id + ")", "Delete")
                        }
                        td ""
                        td {
                            button(class: "btn btn-primary", "Save")
                        }
                        td {
                            a(class: "btn btn-link", href: "/book", "Back")
                        }
                    }
                }
            }
        }

script '''
function deleteBook(id) {
    if (confirm("Delete? " + id)) {
        ajaxDelete("/api/book/" + id, function (result) {
            location.href = "/book/list";
        });
    }
}
'''
