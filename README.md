# IBCS IA: Home Services App
My client is a Physical Education teacher at my school and he has had difficulty trying to find home services, such as plumbers and electricians, in Hong Kong.  A main issue is that he does not know Chinese or Cantonese, hence making it difficult for him to contact such services.  
There is currently no central platform in Hong Kong that allows for searches or contacts of service providers.  
My client stated that “when you need a service, it’s usually in short notice”, therefore a solution should allow efficient searches of available and relevant service providers.  
Since severity of situations can vary for such services, the service requester should also provide photos of the situation.  For example, they should upload a photo of a blocked sink.  This requires my solution to allow users to upload images while submitting requests. 

The proposed solution is to create a mobile app simplifying the whole process, from filing a request to leaving a review of the service provider.  When filing a request, the requester should specify language, location, and service type, while providing a detailed description and images, to aid service providers to understand the situation.  
Providers will then suggest a price, known as quota, as requested by client.   
The customer can either accept or decline, depending whether they think the price is acceptable or not.  Once accepted, there should be a chat feature, an alternative I suggested and agreed by client, to allow communication to remain on the app.  
My solution is more effective than current procedures as it creates a central platform for people to find service providers more conveniently, allow communication between users, and allow users to review providers’ service quality to prevent others from receiving bad services.

My solution is written in Java (for Android Studio) and JavaScript (for Firebase Cloud Functions).  Firebase Authentication and Realtime Database was used to authenticate and store data.
