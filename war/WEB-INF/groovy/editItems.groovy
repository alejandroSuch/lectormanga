import com.google.appengine.api.datastore.*
import java.util.logging.Logger;

if(params){
	if(params.submit == "save") {
		def entity = new Entity("Item")
		entity.desc = params.desc
		entity.amount = params.amount
		entity.save()
	} else if(params.submit == "delete"){
		def key = KeyFactory.createKey("Item", KeyFactory.stringToKey(params.key).id)
		def entity = datastore.get(key)
		entity.delete()
	} else if(params.submit == "update") {
		def key = KeyFactory.createKey("Item", KeyFactory.stringToKey(params.key).id)
		def entity = datastore.get(key)
		entity.desc = params.desc
		entity.amount = params.amount
		entity.save()
	}
}

//Get all items in the database
def query = new Query("Item")
def entityList = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(100))

request.setAttribute "entityList", entityList

//Entity entity = new Entiy("item")

forward "index.gtpl"