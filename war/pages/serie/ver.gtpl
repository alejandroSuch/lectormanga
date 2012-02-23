<!DOCTYPE HTML>
<html>
  <head>
    <title>:: Lector manga - <%=request.getAttribute('name') %> <%=request.getAttribute('chapter') %> ::</title>
    <link href='http://fonts.googleapis.com/css?family=Just+Me+Again+Down+Here' rel='stylesheet' type='text/css'>
    <script language="javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
    	var baseUrl = '<%=request.getAttribute("images")%>';
    	var i = 1;
    	var imageList = new Array();

    	var totalImages;
    	var loadedImages = 0;
    	var images = new Array();
    	var canvas = false;
    	var context = false;
    	var currentImage = 0;
    	var dragging = false;
    	var lastX = 0;
    	var fps = 24;
    	var startX = 0;
    	var endX = 0;
    	var startTime = 0;
    	var endTime = 0;
    	var img;

    	/*var addImage = function(){
		var img = new Image();
		img.src = baseUrl + i + ".jpg";
		img.onerror = function(){
		};

		img.onload = function(){
			var imgUrl = baseUrl+i+'.jpg';

			i++;
			addImage();
		};
		};*/
    	

    	var loadImages = function(){
       		img = new Image();
       		img.src = baseUrl+i+'.jpg';
       		img.onload = imageLoaded;
       		img.onerror = end;
        };

        var end = function(){
        	document.title = ':: Lector manga - <%=request.getAttribute('name') %> <%=request.getAttribute('chapter') %>. Pagina 1 de '+images.length+' ::';
        	calculateSizes();
        	calculateCurrentImage();
           	drawCurrentImage();
        };

        var imageLoaded = function(){
            i++;
            loadedImages++;
            totalImages = loadedImages;
            progress();

            images.push({
           		image    : img,
           		width    : 0,
           		height   : 0,
           		lastX	 : 0
            });

            loadImages();
            
        };

        var calculateSizes = function(){
        	var cw = canvas.width;
            var ch = canvas.height;
            
           	for(i in images){
               	var img = images[i].image;
               	
               	var iw = img.width;
                var ih = img.height;
                
                var width = 0;
                var height = 0;

                if(cw > ch){
                	if(iw > ih){
                		width = cw;
                		height = cw*ih/iw;
                		if(height > ch){
                    		height = ch;
                            width = iw*ch/ih;
                    	}
                    } else {
                    	height = ch;
                        width = iw*ch/ih;
                        if(width > cw){
                        	height = ch;
                            width = iw*ch/ih;
                        }
                    }
                } else {
                	if(iw > ih){
                        width = cw;
                        height = ih*cw/iw;
                        if(height > ch){
                    		height = ch;
                            width = iw*ch/ih;
                    	}
                    } else {
                        height = ch;
                        width = iw*ch/ih;
                        if(width > cw ){
                            width = cw;
                    		height = cw*ih/iw;
                        }
                    }
                }

                images[i].width  = width;
                images[i].height = height;
                images[i].lastX = (canvas.width-width)/2 + (i-currentImage)*(canvas.width);// - (canvas.width-width)/2);
            }
        };

        var drawCurrentImage = function(offsetX){          
	            if(!offsetX){
	            	for(i in images){  
	                	context.drawImage(images[i].image, images[i].lastX, (canvas.height-images[i].height)/2, images[i].width, images[i].height);
	               	}
		           	//context.drawImage(images[currentImage].image, images[currentImage].lastX, (canvas.height-images[currentImage].height)/2, images[currentImage].width, images[currentImage].height);
	            }else {
	            	for(i in images){  
		                if(lastX == 0){
		                    lastX = offsetX;
		                }
		
		                if(lastX < offsetX){
		                    images[i].lastX+= offsetX - lastX;
		                } else {
		                    images[i].lastX-= lastX - offsetX;
		                }
		
		            	context.drawImage(images[i].image, images[i].lastX, (canvas.height-images[i].height)/2, images[i].width, images[i].height);
	            	}
	            	lastX = offsetX;
	            }
        };

        var clearCanvas = function(){
        	context.fillStyle = "#000000";
            context.fillRect(0, 0, canvas.width, canvas.height);
        };

        // http://www.ascendedarcade.com/content/how-use-google-web-fonts-your-html5-canvas
        var progress = function(){
            clearCanvas();
            context.font = "normal 40px 'Just Me Again Down Here'";
            context.textAlign = 'center';
            context.fillStyle = '#FFFFFF';
            context.fillText('Cargando manga...', canvas.width/2, canvas.height/2 - 25);
            context.fillText('Pag. ' + loadedImages, canvas.width/2, canvas.height/2 + 25);
        };

    	var resizeReadingZone = function(){
        	lastX = 0;
    		canvas.width = \$(window).width();
			canvas.height = \$(window).height();
			if(images[currentImage]){
				clearCanvas();
				calculateSizes();
	           	drawCurrentImage(false);
			}
        };

        var init = function(){
            canvas = document.getElementById('readingZone');
            context = canvas.getContext('2d');
        };

        var calculateCurrentImage = function(){
            for(i in images){
                if(currentImage != i && Math.abs(images[i].lastX+images[i].width/2 - canvas.width/2) <= Math.abs(images[currentImage].lastX - canvas.width/2)){
                    currentImage = i;
                    document.title = ':: Lector manga - <%=request.getAttribute('name') %> <%=request.getAttribute('chapter') %>. Pagina '+(parseInt(i)+1)+' de '+images.length+' ::';
                    //window.history.pushState({}, "Pagina" + (i+1), images[i].image.src);
                }
            }
        };

        var goToCurrent = function(){
        	var interval = setInterval(
					function(){
						var distance = (canvas.width-images[currentImage].width)/2 - images[currentImage].lastX;
						clearCanvas();

						for(i in images){
							images[i].lastX = images[i].lastX + distance / 2;
							context.drawImage(images[i].image, images[i].lastX, (canvas.height-images[i].height)/2, images[i].width, images[i].height);
						}

						if(Math.abs(distance) < 1){
							clearInterval(interval);
						}
					}, 
					1000/fps
				);
		};
    	
		\$(document).ready(function(){
			init();
			resizeReadingZone();
			clearCanvas();
			progress();
			loadImages();
			
			\$(window).resize(resizeReadingZone);
			
			\$("#readingZone").mousedown(function(event){
				dragging = true;
			});
			
			\$("#readingZone").mouseup(function(event){
				lastX = 0;
				dragging = false;
				calculateCurrentImage();

				goToCurrent();
				
			});

			\$("#readingZone").mousemove(function(event){
				if(dragging){
					clearCanvas();
					calculateCurrentImage();
					drawCurrentImage(event.clientX);
				}
			});

			\$("#readingZone").bind('touchstart', function(event){
				dragging = true;
			});
			
			\$("#readingZone").bind('touchend', function(event){
				lastX = 0;
				dragging = false;
				calculateCurrentImage();

				goToCurrent();
				
			});

			\$("#readingZone").bind('touchmove', function(event){
				if(dragging){
					if((event.originalEvent.touches && event.originalEvent.touches.length >= 1) || (event.originalEvent.changedTouches && event.originalEvent.changedTouches.length >= 1)){ // Only deal with one finger
						var touch = event.originalEvent.touches[0] || event.originalEvent.changedTouches[0]; // Get the information for finger #1
						clearCanvas();
						calculateCurrentImage();
						drawCurrentImage(touch.clientX);
						console.log(touch.clientX+','+touch.clientY);
					}
				}
			});

			\$(document).keyup(function(event){
				if(event.keyCode == 37){
					if(currentImage > 0){
						currentImage--;
						goToCurrent();
						return;
					}
				}

				if(event.keyCode == 39){
					if(currentImage < images.length-1){
						currentImage++;
						goToCurrent();
						return;
					}
				}
			});
			
		});
    </script>
    <style>
    	html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed, 
figure, figcaption, footer, header, hgroup, 
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, 
footer, header, hgroup, menu, nav, section {
	display: block;
}
body {
	line-height: 1;
}
ol, ul {
	list-style: none;
}
blockquote, q {
	quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
	content: '';
	content: none;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
}
    </style>
  </head>

  <body>
    <canvas id="readingZone" width="640" height="480"></canvas>
  </body>
</html>
