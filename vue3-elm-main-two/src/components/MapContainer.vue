<template>
    <div>
      <div id="container"></div>
    </div>
  </template>
  
  <script setup>
  
      var map = new AMap.Map("container", {
        resizeEnable: true
    });

  </script>
  <style>
    #container {
      width: 80%;
      height: 400px;
      margin: 100px auto;
      border: 1px solid red;
    }
  </style>