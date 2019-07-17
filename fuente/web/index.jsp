<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>MangoChatBot</title>
        <link rel="stylesheet" href="assets/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="css/app.css">
    </head>
    <body>
        <div class="box-chat">
            <div class="chat-header">
                <div class="bot-avatar">
                    <img src="img/avatar.png" alt="avatar">
                </div>
                <h4>Mango Asistente Virtual</h4>
                <h5>En línea</h5>
                <div class="chat-close" data-mode="0">
                    <i class="fas fa-chevron-up" id="icon-chat"></i>
                </div>
            </div>
            <div class="chat-hidden">
                <div class="chat-body">
                    <div id="chat-messages"></div>
                    <div class="clear" id="chat-final"></div>
                </div>
                <div class="chat-footer">
                    <form action="#" method="POST" id="chat-form">
                        <div class="chat-input">
                            <input type="text" name="message" id="message" placeholder="Escribe tu mensaje...">
                        </div>
                         <input type="hidden" name="latitud" id="latitud" value="">
                         <input type="hidden" name="longitud" id="longitud" value="">
                        <div>
                            <button type="submit" class="btn-send">
                                <i class="fas fa-location-arrow fa-rotate-45"></i>
                            </button>
                        </div>
                    </form>
                    
                </div>
            </div>
        </div>
        
        <script src="assets/scrollBar/jquery.min.js"></script>
        <script>
            
            function obtenerUbicacion(){
                navigator.geolocation.getCurrentPosition(showPosition);
                //navigator.geolocation.watchPosition(showPosition);
            }
            
            function getHoraActual()
            {
                var f=new Date();
                var hora;
                var minutos;
                minutos = f.getMinutes();
                if (minutos<10)
                {
                    minutos = '0'+minutos;
                }
                hora = f.getHours()+":"+minutos;
                
                return hora;
            }

            function showPosition(position){
                var latitud = position.coords.latitude;
                var longitud = position.coords.longitude;
                
                $('#latitud').val(latitud);
                $('#longitud').val(longitud);
                
                var linkPosicion = '<a href="https://maps.google.com/?q='+latitud+','+longitud+'" target="_blank">Ver tu ubicaci&oacute;n en el mapa</a>';
                
                $('#chat-messages').append('<div class="chat-package"><div class="spinner last-spining"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div><div class="chat-message message-bot  display-hide last-message">'+linkPosicion+'<span class="message-time">'+getHoraActual()+'</span></div><div class="clear"></div><div>');
                $('#chat-messages').append('<div class="chat-package"><div class="spinner last-spining"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div><div class="chat-message message-bot  display-hide last-message">Por favor, dime cual es tu nombre de usuario...<span class="message-time">'+getHoraActual()+'</span></div><div class="clear"></div><div>');

                setTimeout(function(){
                    $('.last-spining').hide(); 
                    $('.last-message').fadeIn('fast');
                    $('.last-spining').removeClass('last-spining');
                    $('.last-message').removeClass('last-message');
                    $('.chat-body').scrollTop($('.chat-body')[0].scrollHeight);
                }, 300);
            }

            jQuery(document).ready(function($) {
                $('.chat-close').click(function(e) {
                    if ($(this).data('mode') == 0) {
                        $(this).data('mode', 1);
                        $('.chat-hidden').slideDown('slow', function(){
                            if ($('#chat-messages').text() == '') {
                                $('#chat-messages').append('<div class="chat-package"><div class="spinner first-message last-spining"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div><div class="chat-message message-bot first-message display-hide last-message">Hola! me permites conocer tú ubicación?<span class="message-time">'+getHoraActual()+'</span></div><div class="clear"></div></div>');

                                setTimeout(function(){
                                    $('.last-spining').hide(400, function(){
                                        $('.last-message').fadeIn('fast');
                                        $('.last-spining').removeClass('last-spining');
                                        $('.last-message').removeClass('last-message');
                                        obtenerUbicacion();
                                    });
                                }, 500);

                                $(window).trigger("resize.scrollBox");
                            }
                        });
                        $('#icon-chat').removeClass('fa-chevron-up');
                        $('#icon-chat').addClass('fa-chevron-down');
                        $('.chat-header').addClass('chat-header-click');
                    }
                    else{
                        $(this).data('mode', 0);
                        $('.chat-hidden').slideUp('slow');
                        $('#icon-chat').removeClass('fa-chevron-down');
                        $('#icon-chat').addClass('fa-chevron-up');
                        $('.chat-header').removeClass('chat-header-click');
                    }
                });

                $( "#chat-form" ).submit(function( event ) {
                  event.preventDefault();
                    var mensajeUsuario = $("#message").val();
                    var latitud = $("#latitud").val();
                    var longitud = $("#longitud").val();
        
                    $('#chat-messages').append('<div class="chat-package"><div class="spinner float-right last-spining"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div><div class="chat-message message-user  display-hide last-message">'+mensajeUsuario+'<span class="message-time">'+getHoraActual()+'</span></div><div class="clear"></div><div>');
                    $("#message").val('');

                    setTimeout(function(){
                        $('.last-spining').hide(); 
                        $('.last-message').fadeIn('fast');
                        $('.last-spining').removeClass('last-spining');
                        $('.last-message').removeClass('last-message');
                        $('.chat-body').scrollTop($('.chat-body')[0].scrollHeight);
                    }, 300);
                    
                    $.ajax({
                        url: 'Controlador',
                        type: 'POST',
                        data: {message: mensajeUsuario, latitud: latitud, longitud: longitud},
                      })
                      .done(function(data) {
                          var paso;
                          var nummensajes = data['cantidad'];
                          var indiX;
                          
                          alert("llego " + data)
                          
                          for (paso = 0; paso < nummensajes; paso++) {
                            indiX = 'msm'+paso.toString();
                            $('#chat-messages').append('<div class="chat-package"><div class="spinner last-spining"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div><div class="chat-message message-bot  display-hide last-message">'+
                                                data[indiX]+
                                                '<span class="message-time">'+getHoraActual()+'</span></div><div class="clear"></div><div>');
                          }

                            setTimeout(function(){
                              $('.last-spining').hide(); 
                              $('.last-message').fadeIn('fast');
                              $('.last-spining').removeClass('last-spining');
                              $('.last-message').removeClass('last-message');
                              $('.chat-body').scrollTop($('.chat-body')[0].scrollHeight);
                          }, 1000);
                      })
                      .fail(function() {
                        alert("error");
                      })
                      .always(function() {
                        console.log("complete");
                      });
                });
            });
        </script>
    </body>
</html>
    