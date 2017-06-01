$(document).ready(function(){
  $(window).scroll(function(){
    var winTop = $(window).scrollTop();
    if(winTop >= 30){
      $("body").addClass("sticky");
    }else{
      $("body").removeClass("sticky");
    }
  });

  $(".navToggle").click (function(){
    $(this).toggleClass("open");
    $("nav").toggleClass("open");
  });
});
