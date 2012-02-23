import util.*;

def reader = new SMReader();
def images = reader.getImages4("http://submanga.me/" + URLEncoder.encode(params.name))

def name = params.name
def tokens = name.tokenize("/")

request.setAttribute "images", images
request.setAttribute "name", tokens[0].replaceAll("_", " ")
request.setAttribute "chapter", tokens[1]
request.setAttribute "seoURL", "http://lectormanga.appspot.com/" + params.name
request.setAttribute "fbImg", images[(int)(images.size()/2)]

forward "pages/serie/ver.gtpl"