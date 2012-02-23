import util.*;

def reader = new SMReader();

def name = params.name
out.print(name)


def chapters = reader.getChapterList(name)

request.setAttribute "chapters", chapters
request.setAttribute "name", name.replaceAll("_", " ")

forward "pages/serie/list.gtpl"