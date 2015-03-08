var arDrone = require('ar-drone');
var client  = arDrone.createClient();

client.takeoff();

client.after(5000, function(){
client.land();
});

client.after(1000, function(){
console.log("test complete successful");
process.exit(0);
});

