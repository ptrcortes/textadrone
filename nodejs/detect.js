//Thomas Schucker
var arDrone = require('ar-drone');
var client  = arDrone.createClient();

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
	client.after(10000, function() {
		this.takeoff();
	});	
	client.after(10000, function() {
	   this.clockwise(0.15);
 	});
	client.after(2000, function() {
	    this.stop();
	    this.land();
	  });
	client.after(1000, function(){
			console.log("succesful"); 
			process.exit(0);
	});