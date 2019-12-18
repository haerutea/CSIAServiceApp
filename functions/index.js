/* eslint-disable no-template-curly-in-string */
const functions = require('firebase-functions');
//all code from https://github.com/firebase/functions-samples/tree/master/fcm-notifications
const admin = require('firebase-admin');
admin.initializeApp();
/**
 * Triggers when a user gets a new requester and sends a notification.
 *
 * Requests adds a flag to `/requests/{customerUid}/{requestTitle}/quotas/{itemNum}/{serProviderUid}`.
 * Users save their device notification tokens to `/users/{customerUid}/token/{notificationToken}`.
 */

 //TODO: CHANGE STRING
exports.requestNotif = functions.database.ref('/requests/{inRid}/quotas/{itemNum}')
    .onCreate((snapshot, context) => 
    {
    //get variables from new data
    const quota = snapshot.val();
    const serProviderUid = quota.providerUid;
    //const submitterUid = quota.submitterUid;
    const rid = context.params.inRid;
    
    console.log('We have a new requester UID:', serProviderUid, 'for request:', rid);
    
    getUid(rid).then(x => {
      console.log("submit: " + x);
      sendNotif(x, serProviderUid);
      return;
    }).catch(console.log);

    //console.log("submitter2: " + submitterUid);
    
    // Get the list of device notification tokens.
    /*const deviceTokensPromise = admin.database()
        .ref(`/users/${submitterUid}/token`).once('value');

    // Get the requester profile.
    const requesterProfilePromise = admin.auth().getUser(serProviderUid);

    // The snapshot to the user's tokens.
    let tokensSnapshot;
    // The array containing all the user's tokens.
    let tokens;

    return Promise.all([deviceTokensPromise, requesterProfilePromise]).then(results => {
      tokensSnapshot = results[0];
      const requester = results[1];

      // Check if there are any device tokens.
      if (!tokensSnapshot.hasChildren()) {
        return console.log('There are no notification tokens to send to.');
      }
      console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
      console.log('Fetched requester profile', requester);

      // Notification details.
      const payload = {
        notification: {
          title: 'You have a new quota request!',
          body: `${requester.displayName} offered a price for your request.`,
        }
      };

      // Listing all tokens as an array.
      tokens = Object.keys(tokensSnapshot.val());
      console.log("tokens: ", tokens);
      // Send notifications to all tokens.
      return admin.messaging().sendToDevice(tokens, payload);
    }).then((response) => {
      // For each message check if there was an error.
      const tokensToRemove = [];
      response.results.forEach((result, index) => {
        const error = result.error;
        if (error) {
          console.error('Failure sending notification to', tokens[index], error);
          // Cleanup the tokens who are not registered anymore.
          if (error.code === 'messaging/invalid-registration-token' ||
              error.code === 'messaging/registration-token-not-registered') {
            tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
          }
        }
      });
      return Promise.all(tokensToRemove);
    });*/
      return;
});

function sendNotif(x, providerUid)
{
  const deviceTokensPromise = admin.database()
        .ref(`/users/${x}/token`).once('value');

    // Get the requester profile.
    const requesterProfilePromise = admin.auth().getUser(providerUid);

    // The snapshot to the user's tokens.
    let tokensSnapshot;
    // The array containing all the user's tokens.
    let tokens;

    return Promise.all([deviceTokensPromise, requesterProfilePromise]).then(results => {
      tokensSnapshot = results[0];
      const requester = results[1];

      // Check if there are any device tokens.
      if (!tokensSnapshot.hasChildren()) {
        return console.log('There are no notification tokens to send to.');
      }
      console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
      console.log('Fetched requester profile', requester);

      // Notification details.
      const payload = {
        notification: {
          title: 'You have a new quota request!',
          body: `${requester.displayName} offered a price for your request.`,
        }
      };

      // Listing all tokens as an array.
      tokens = Object.keys(tokensSnapshot.val());
      console.log("tokens: ", tokens);
      // Send notifications to all tokens.
      return admin.messaging().sendToDevice(tokens, payload);
    }).then((response) => {
      // For each message check if there was an error.
      const tokensToRemove = [];
      response.results.forEach((result, index) => {
        const error = result.error;
        if (error) {
          console.error('Failure sending notification to', tokens[index], error);
          // Cleanup the tokens who are not registered anymore.
          if (error.code === 'messaging/invalid-registration-token' ||
              error.code === 'messaging/registration-token-not-registered') {
            tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
          }
        }
      });
      return Promise.all(tokensToRemove);
    });
}
//https://javascript.info/async-await
async function getUid(requestId)
{
  let databaseRef = admin.database().ref("requests/" + requestId);
  let submitterUidPromise = new Promise((resolve, reject) => databaseRef.once("value", (snapshot) => {
    resolve(snapshot.child("submitterUid").val());
    console.log("submitterUid: " + snapshot.child("submitterUid").val());
  }, (errorObject) => {
    console.log("The read failed: " + errorObject.code);
  }));
  let uid = await submitterUidPromise;
  return uid;
}

/*
exports.deletedRequest = functions.database.ref('/requests/{customerUid}/{requestTitle}/quotas/{itemNum}/providerUid/{serProviderUid}')
    .onDelete((change, context) => {
      //get variables from new data
      const serProviderUid = context.params.serProviderUid;
      const customerUid = context.params.customerUid;
      // If deleted request, we exit the function.
      console.log(serProviderUid, 'deleted request for user:', customerUid);

      // Get the list of device notification tokens.
      const deviceTokensPromise = admin.database()
          .ref(`/users/${customerUid}/token`).once('value');

      // Get the requester profile.
      const requesterProfilePromise = admin.auth().getUser(serProviderUid);

      // The snapshot to the user's tokens.
      let tokensSnapshot;
      // The array containing all the user's tokens.
      let tokens;

      return Promise.all([deviceTokensPromise, requesterProfilePromise]).then(results => {
        tokensSnapshot = results[0];
        const requester = results[1];

        // Check if there are any device tokens.
        if (!tokensSnapshot.hasChildren()) {
          return console.log('There are no notification tokens to send to.');
        }
        console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
        console.log('Fetched requester profile', requester);

        // Notification details.
        const payload = {
          notification: {
            title: 'Deleted request',
            body: `${requester.displayName} deleted the request`,
          }
        };

        // Listing all tokens as an array.
        tokens = Object.keys(tokensSnapshot.val());
        console.log("tokens: ", tokens);
        // Send notifications to all tokens.
        return admin.messaging().sendToDevice(tokens, payload);
      }).then((response) => {
        // For each message check if there was an error.
        const tokensToRemove = [];
        response.results.forEach((result, index) => {
          const error = result.error;
          if (error) {
            console.error('Failure sending notification to', tokens[index], error);
            // Cleanup the tokens who are not registered anymore.
            if (error.code === 'messaging/invalid-registration-token' ||
                error.code === 'messaging/registration-token-not-registered') {
              tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
            }
          }
        });
        return Promise.all(tokensToRemove);
      });
});*/