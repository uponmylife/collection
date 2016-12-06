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
                        td(colspan: "4") {
                            div(class: "row") {
                                div(class: "col-xs-offset-1 col-xs-1") {
                                    span "read"
                                }
                                div(class: "col-xs-4") {
                                    select(name: "readAt", class: "form-control") {
                                        option ""
                                        cal = Calendar.getInstance()
                                        sm = book.readAt == null ? m : book.readAt.format("yyyy.M")
                                        12.times {
                                            m = cal.getTime().format("yyyy.M")
                                            yieldUnescaped "<option ${m == sm ? "selected" : ""}>$m</option>"
                                            cal.roll(Calendar.MONTH, false)
                                        }
                                    }
                                }
                                div(class: "col-xs-1") {
                                    span "rank"
                                }
                                div(class: "col-xs-3") {
                                    select(name: "rank", class: "form-control") {
                                        for (r in 1..5) {
                                            yieldUnescaped "<option ${book.rank == r ? "selected" : ""}>$r</option>"
                                        }
                                    }
                                }
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
//                            a(class: "btn btn-link", href: "/book", "Back")
                            a(class: "btn btn-link", target: "_blank",
                                    href: "http://www.yonginlib.go.kr/dongbaek/book_search/search_list.asp?sort=RK+DESC&msa=M&limitpage=100&local=MF&field1=IT&value1=" + book.title,
                                    "Library search")
                        }
                    }
                }
            }
        }

script '''
function deleteBook(id) {
    if (confirm("Delete? " + id)) {
        ajaxDelete("/api/book/" + id, function (result) {
            location.href = "/book";
        });
    }
}
'''
