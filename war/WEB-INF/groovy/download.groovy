import util.*;
import java.util.zip.*;

def reader = new SMReader();
def images = reader.getImages3("http://submanga.me/" + URLEncoder.encode(params.name))
def tokens = params.name.tokenize("/")
def name =  tokens[0].replaceAll("_", " ")
def chapter = tokens[1]
def os = response.getOutputStream()
def zos = new ZipOutputStream(os)

response.setContentType 'application/cbz'
response.setHeader "Content-Disposition", "attachment; filename=" + name + ' ' + chapter + '.cbz';

images.eachWithIndex{ image, index ->
	zos.putNextEntry(new ZipEntry((index+1).toString().padLeft(4,'0') + '.jpg'))
	try{
		def bytes = new URL(image).getBytes()
		zos.write(bytes, 0, bytes.size())
	} catch(Exception e) {}
	zos.closeEntry()
}

zos.flush()
zos.close()