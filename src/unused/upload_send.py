access_token = "IGq2mLEAeLcAAAAAAAAACdK5Lqs8SWHp-IcKA4xO38vy8B7WgTHa9bYaGFPqsh-P"
 
import datetime
timestamp = datetime.datetime.now().strftime("%h-%m-%S")
filename = "dronepic" + timestamp + ".jpg"
 
import dropbox
client = dropbox.client.DropboxClient(access_token)
f = open("./pic.jpg")
response = client.put_file(filename, f)
url = client.media(response['path'])['url']
print "successful" + url

'''
twilio_phone_number = "+14242924689"
account_sid = "PN487c2bca672c08f6824a6dc4706914d8"
auth_token = "620bf1fc6865d6274b2df9fbe9b9a149"
 
from twilio.rest import TwilioRestClient
 
client = TwilioRestClient(account_sid, auth_token)
messages = client.messages.list(to=twilio_phone_number)
for msg in messages:
    number = msg.from_
    try: client.messages.create(
    to=number,
    from_=twilio_phone_number,
    body="Hello from hack AZ!",
    media_url=url)
    except:
        pass
 
print number
'''

