<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>HomeWork</title>
    <style>
        .container{
            display: flex;
            flex-direction: column;
            justify-content: center;
            height: 100vh;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Enter form</h1>
        <form class="center-block shadow-sm p-3 mb-5 bg-body-tertiary rounded" action="${pageContext.request.contextPath}/request" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="nameInput" aria-describedby="name">
            </div>
            <div class="mb-3">
                <label for="surName" class="form-label">Surname</label>
                <input type="text" class="form-control" id="surName" name="surName">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>