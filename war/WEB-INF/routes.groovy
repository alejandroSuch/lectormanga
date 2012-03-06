all "/", forward: "/seriesList.groovy", cache: 8760.hours //1 a–o
all "/series", forward: "/seriesList.groovy", cache: 8760.hours //1 a–o
all "/serie/ver/@name", forward: "/serieVer.groovy?name=@name", cache: 33125.hours //5 a–os
all "/serie/descargar/@name", forward: "/download.groovy?name=@name", cache: 33125.hours //5 a–os
get "/serie/@name", forward: "/serie.groovy?name=@name", cache: 72.hours //3 d’as
get "/cache/clear", forward: "/clearcache.groovy"