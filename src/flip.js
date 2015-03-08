var arDrone = require('ar-drone');
var client  = arDrone.createClient();

client.takeoff();

client
  .after(5000, function() {
    this.animate('flipBehind', 1000);
  })
  .after(3000, function() {
    this.stop();
    this.land();
  });
	.after(1000, function(){
		console.log("succesful"); 
		process.exit(0);
	})