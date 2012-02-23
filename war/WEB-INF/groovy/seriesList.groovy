import util.*;

def reader = new SMReader();

def series = reader.getSeriesList()

request.setAttribute "series", series

forward "pages/series/list.gtpl"