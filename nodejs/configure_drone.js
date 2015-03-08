var arDrone = require('ar-drone');
var client  = arDrone.createClient();

client.config('control:altitude_max', '1500');
client.config('control:control_vzmax', '500');
client.config('control:control_yaw', '2.0');
client.config('euler_angle_max', '0.25');

client.after(1000, function(){
	console.log("successful"); 
	process.exit(0);
});