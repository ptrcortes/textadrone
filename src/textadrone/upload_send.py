access_token = ""
 
import datetime
timestamp = datetime.datetime.now().strftime("%h-%m-%S")
filename = "hackaz" + timestamp + ".jpg"
print filename
 
import dropbox
client = dropbox.client.DropboxClient(access_token)
f = open("/mnt/sda1/pic.jpg")
response = client.put_file(filename, f)
url = client.media(response['path'])['url']
print url
 
twilio_phone_number = "+15202241026"
account_sid = ""
auth_token = ""
 
from twilio.rest import TwilioRestClient
 
client = TwilioRestClient(account_sid, auth_token)
messages = client.messages.list(to = twilio_phone_number)
for msg in messages:
    number = msg.from_
    try: client.messages.create(
    to = number,
    from_ = twilio_phone_number,
    body = "Hello from hack AZ!",
    media_url = url)
    except:
        pass
 
print number 