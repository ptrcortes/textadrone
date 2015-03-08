//Thomas Schucker
// Run this to receive a png image stream from your drone and saves it to a directory.

var arDrone = require('ar-drone');
var fs = require('fs');
var i = 0;
console.log('Connecting png stream ...');

var pngStream = arDrone.createClient().getPngStream();

var lastPng;
pngStream
  .on('error', console.log)
  .on('data', function(pngBuffer) {
    lastPng = pngBuffer;
		fs.writeFile("/Users/tschucker/arpics/arImage" + i.toString() +".png", lastPng,'base64', function(err) {
		    if(err) {
		        console.log(err);
		    } else {
		        console.log("The file was saved!");
						i = i +1;
		    }
		});
  });

