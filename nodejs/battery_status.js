//Thomas Schucker
var arDrone = require('ar-drone');
var client  = arDrone.createClient();
client.after(1000, function(){
	console.log(this.battery()); 
	console.log("successful");
	process.exit(0);
});