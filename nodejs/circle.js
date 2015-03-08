//Thomas Schucker
var arDrone = require('ar-drone');
var client  = arDrone.createClient();

client.takeoff();

client
  .after(5000, function() {
    this.clockwise(0.25);
  })
  .after(3000, function() {
    this.stop();
    this.land();
  });
	.after(1000, function(){
		console.log("succesful"); 
		process.exit(0);
	});