Parse.Cloud.job('deleteOldPosts', function(request, status) {

    // All access
    Parse.Cloud.useMasterKey();

    var today = new Date();
    var time = (6 * 3600 * 1000);
    var expirationDate = new Date(today.getTime() - (time));

    var query = new Parse.Query('Nowcast');
        // All posts have more than 6 hours
        query.lessThan('createdAt', expirationDate);

        query.find().then(function (posts) {
            Parse.Object.destroyAll(posts, {
                success: function() {
                    status.success('All posts are removed.');
                },
                error: function(error) {
                    status.error('Error, posts are not removed.');
                }
            });
        }, function (error) {});

});