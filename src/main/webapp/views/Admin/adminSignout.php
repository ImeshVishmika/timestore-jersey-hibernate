<?php

/**
 * Admin Sign Out Handler
 * Calls the API endpoint to sign out and clear session
 * Then redirects to the login page
 */

session_start();

// Destroy the session
$_SESSION = [];
session_destroy();

// Clear remember me cookies if they exist
if (isset($_COOKIE['email'])) {
    setcookie('email', '', time() - 3600, '/');
}
if (isset($_COOKIE['pw'])) {
    setcookie('pw', '', time() - 3600, '/');
}

// Redirect to admin login page
header("Location: signin.php");
exit();
?>
