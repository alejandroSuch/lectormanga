package util;

import java.util.zip.*;

def slurp(url){
	def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser())
	slurper.parse(url)
}

def processList(url){
	def result = []
	def html = slurp(url)
	
	try{
		html.body.'**'.find{
		  it.@class.text() == 'caps'
		}.'**'.findAll{
		  it.name() == 'tr'
		}.eachWithIndex{ it, i ->
		  if(it.children()[0].name() != 'th'){
			link = it.children()[0].a.@href
			a = it.children()[0].a
			
			a.children()[2] = '';
			a.children()[1] = '';
			
			chapter = a.text().trim()
			
			/*println "capitulo $chapter"
			println "enlace $link"
			println()
			*/
			result << ['chapter':chapter, 'link':link]
		  }
		}
	} catch(NullPointerException npe) { }
	return result
}

def getSeriesList() {
	return processList('http://submanga.me/series')
}

def getChapterList(seriesName){
	return processList('http://submanga.me/' + seriesName + '/completa')
}

def getChapterURL(url){
	def splits = url.tokenize("/")
	splits[0] + '//' + splits[1] + '/' + 'c/' + splits[splits.size()-1]
}

def getImages(chapterUrl){
	def list = []
	def html = slurp(chapterUrl)
	def numPages = html.body.table.tr[0].th[0].select.children().size()
	def src = html.body.div.a.img.@src
	//Metemos cada imagen en la lista
	(1..numPages).each{
		imgsrc = src.text().replaceAll('1.jpg', it + '.jpg')
		println "SRC = "+imgsrc
		list.add(new URL(imgsrc).getBytes())
	}
		
	return list
}

def getImages2(String url){
	def list = []
	def chapterHtml = slurp(url)
	def firstImg = chapterHtml.body.div[0].div[0].div[3].div[1].div[2].div[0].div[0].@style.text()
	
	return firstImg
//	def numPages = chapterHtml
//	return numPages
//	/*def src = html.body.div.a.img.@src
//	//Metemos cada imagen en la lista
//	(1..numPages).each{
//		imgsrc = src.text().replaceAll('1.jpg', it + '.jpg')
//		list.add(imgsrc)
//	}
		
//	return list*/
}

def getFirstImage(url){
	def list = []
	def html = slurp url
	//def style = html.body.div[0].div[0].div[3].div[1].div[1].div[0].div[0].@style.text()
	
	def style = html.body.div[0].div[2].div[1].div[0].div[0].@style.text()
	
	(style =~ /.*(http.*jpg).*/)[0][1]
}

def exists(url){
	try{
		def con = new URL(url).openConnection()
		//	con.setConnectTimeout(15*1000)
			con.setRequestMethod('GET')
			con.connect()
			
		return con.getResponseCode() != 404
	} catch(java.net.SocketTimeoutException ste) {
		return true;
	}
}

def getImages3(url){
	def first = getFirstImage(url)
	def result = []
	def failures = 0
	def i = 1
	
	while(failures < 3){
		def img = first.replaceAll('1.jpg', i+'.jpg')
		
		if(exists(img))
			result << img
		else
			failures++
	
		i++
	}
	
	result
}

def getImages4(url){
	getFirstImage(url).replaceAll('1.jpg', '')
}

def makeZip(images){
	tmpZipFile = File.createTempFile("tmpZipFile", ".zip")
	zos = new ZipOutputStream(tmpZipFile.newOutputStream()) //En web, no me hace falta crear temporal y uso como outputstream el de la pantalla
	
	images.eachWithIndex{ image, i->
		zos.putNextEntry(new ZipEntry(i.toString().padLeft(4,'0') + '.jpg'))
		zos.write(image, 0, image.size())
		zos.closeEntry()
	}
	zos.close()
	
	"Archivo: $tmpZipFile"
}

def zipImages = { chapterUrl, fileName ->
	def file = new File('/Users/Alejandro/Desktop/smdlr/SSLC/'+fileName+'.cbz')
	def zos = new ZipOutputStream(file.newOutputStream());

	getImages2(chapterUrl).eachWithIndex{ image, index ->
		zos.putNextEntry(new ZipEntry((index+1).toString().padLeft(4,'0') + '.jpg'))
		println "procesando imagen $image"
		try{
			def bytes = new URL(image).getBytes()
			zos.write(bytes, 0, bytes.size())
		} catch(Exception e) {}
		zos.closeEntry()
	}
	println 'Creado /Users/Alejandro/Desktop/smdlr/SSLC'+fileName+".zip"
	println()
	zos.close()
	
}

/* SI NO FUNCIONARA, HABR?A QUE IR PçGINA A PçGINA */
/*
def getImages(chapterUrl){
	list = []
	html = slurp(chapterUrl)
	numPages = html.body.table.tr.td[2].select.option.size()
	src = html.body.center.a.img.@src
	
	(1..numPages).each{
		html = slurp(chapterUrl + '/' + it)
		list[it.toString.padLeft(3,'0')] = new URL(html.body.center.a.img.@src).getBytes()
		//list.add(html.body.center.a.img.@src)
	}
	
	return list
}
*/

/**************** TEST ****************/

/* LISTA DE TODAS LAS SERIES */
// assert getSeriesList().entrySet().size() >= 5000

/* LISTA DE SERIE QUE NO EXISTE */
// series = 'mielda'
// assert getChapterList(series).entrySet().size() == 0

/* LISTA DE SERIE QUE S? QUE EXISTE */
// series = 'Naruto'
// assert getChapterList(series).entrySet().size() > 535

//def seriesName = 'Saint_Seiya_The_Lost_Canvas'
//
//def chapters = getChapterList(seriesName)
//
//def i = 0
//chapters.reverseEach{
//	def pieces = it.chapter.tokenize(" ");
//	def last = pieces.last()
//	def rest = pieces - last
//	def fileName = rest.join(" ") + ' - ' + last.padLeft(4, '0')
//	def chapterUrl = getChapterURL(it.link.toString())
//	zipImages(chapterUrl, fileName)
//}
//
//null
/* TRANSFORMAR LA URL DE UN CAP?TULO, OBTENER SU N?MERO DE PçGINAS Y LA LISTA DE LAS MISMAS*/
/*url = getChapterURL("http://submanga.com/Naruto/36/52496")
// assert url == 'http://submanga.com/c/52496'
// assert getImages(url).size() == 19
images = getImages(url)

println makeZip(images)*/