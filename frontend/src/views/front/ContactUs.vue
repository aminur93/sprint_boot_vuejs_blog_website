<script>
export default {
  name: "FrontContactUs"
}
</script>

<template>
  <div id="contact_us">
    <div class="main">

      <div class="contact_container">

        <p class="h3">Contact Me</p>

        <form action="" class="form">

          <div class="input-wrapper">
            <label for="name">Name</label>
            <input type="text" name="name" id="name" placeholder="Enter your name" required>
          </div>

          <div class="input-wrapper">
            <label for="email">Email</label>
            <input type="email" name="email" id="email" placeholder="Enter your email" required>
          </div>

          <div class="input-wrapper">
            <label for="message">Message</label>
            <textarea name="message" id="message" placeholder="Enter your message" required></textarea>
          </div>

          <button type="submit" class="btn btn-primary">Send Message</button>

        </form>

        <div class="divider"></div>

        <p class="h3">Get In Touch</p>

        <div class="contact_wrapper">

          <div class="icon-box">
            <ion-icon name="mail-outline"></ion-icon>
          </div>

          <div class="contact_wrapper">

            <p class="h3">Email</p>

            <p class="email">
              <a href="mailto:2lKp7@example.com" class="email-link">2lKp7@example.com</a>
            </p>

          </div>

        </div>

        <div class="contact_wrapper">

          <div class="icon-box">
            <ion-icon name="location-outline"></ion-icon>
          </div>

          <div class="contact_wrapper">

            <p class="h3">Location</p>

            <p class="email">
              <a href="#" class="email-link">New York, USA</a>
            </p>

          </div>

        </div>

        <div class="contact_wrapper">

          <div class="icon-box">
            <ion-icon name="call-outline"></ion-icon>
          </div>

          <div class="contact_wrapper">

            <p class="h3">Phone</p>

            <p class="email">
              <a href="tel:+123456789" class="email-link">+123 456 789</a>
            </p>

          </div>

        </div>


      </div>

    </div>
  </div>
</template>

<style scoped>
/*-----------------------------------*\

           #CUSTOM PROPERTY

           write css contac from
\*-----------------------------------*/

.contact_container{
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}


.contact_wrapper {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.contact_wrapper .icon-box {
  flex: 0 0 auto;
  margin-right: 15px;
}

.contact_wrapper .contact_wrapper {
  flex: 1 1 auto;
}

.h3 {
  font-size: 24px;
  margin-bottom: 15px;
}

.divider {
  border-top: 1px solid #ccc;
  margin: 20px 0;
}

/* Form Styles */
.form {
  margin-bottom: 20px;
}

.input-wrapper {
  margin-bottom: 15px;
}

.input-wrapper label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.input-wrapper input,
.input-wrapper textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.input-wrapper textarea {
  resize: vertical;
  min-height: 150px;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
}

.btn-primary:hover {
  background-color: #0056b3;
}

/* Contact Information Styles */
.icon-box {
  width: 40px;
  height: 40px;
  background-color: #007bff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  font-size: 24px;
}

.email-link {
  color: #007bff;
  text-decoration: none;
}

.email-link:hover {
  text-decoration: underline;
}
</style>