<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4">Add New User</h2>
            <form action="adduser" method="post">

                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" required>
                </div>

                <div class="mb-3">
                    <label for="userName" class="form-label">Username</label>
                    <input type="text" class="form-control" id="userName" name="userName" required>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>



                <div class="mb-3">
                    <label for="role" class="form-label">Role ID</label>
                    <select class="form-select" name="role" id="role" required>
                        <option value="" disabled selected>Select Role</option>
                        <option value="6">Staff</option>
                        <option value="7">Team Leader</option>
                        <option value="8">Project Leader</option>
                        <option value="9">Department Leader</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="dept_id" class="form-label">Department</label>
                    <select class="form-select" name="department" id="department" required>
                        <option value="" disabled selected>Select Department</option>
                        <option value="10">Development</option>
                        <option value="11">Marketing</option>
                        <option value="12">Finance</option>
                        <option value="13">HR</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="status" class="form-label">Status</label>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" id="statusSwitch" name="status" value="1" checked>
                        <label class="form-check-label" for="statusSwitch" id="statusLabel">Active</label>
                    </div>
                </div>

                <div class="d-flex justify-content-between mt-4">
                    <button type="submit" class="btn btn-primary">Add User</button>
                    <a href="ListUser" class="btn btn-secondary">Back to List User</a>
                </div>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            const statusSwitch = document.getElementById('statusSwitch');
            const statusLabel = document.getElementById('statusLabel');

            statusSwitch.addEventListener('change', function () {
                if (this.checked) {
                    statusLabel.textContent = 'Active';
                } else {
                    statusLabel.textContent = 'Inactive';
                }
            });
        </script>
    </body>
</html>
