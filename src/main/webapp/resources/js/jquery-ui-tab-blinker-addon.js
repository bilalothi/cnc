(function($){
    // **********************************
    // ***** Start: Private Members *****
    var pluginName = 'blinker';
    var blinkMain = function(data){
        var that = this;
        this.css(data.settings.css_1);
        clearTimeout(data.timeout);
        data.timeout = setTimeout(function(){
            that.css(data.settings.css_0);
        }, data.settings.cycle * data.settings.ratio);
    };
    // ***** Fin: Private Members *****
    // ********************************

    // *********************************
    // ***** Start: Public Methods *****
    var methods = {
        init : function(options) {
            //"this" is a jquery object on which this plugin has been invoked.
            return this.each(function(index){
                var $this = $(this);
                var data = $this.data(pluginName);
                // If the plugin hasn't been initialized yet
                if (!data){
                    var settings = {
                        css_0: {
                            color: $this.css('color'),
                            backgroundColor: $this.css('backgroundColor')
                        },
                        css_1: {
                            color: '#000',
                            backgroundColor: '#F90'
                        },
                        cycle: 2000,
                        ratio: 0.5
                    };
                    if(options) { $.extend(true, settings, options); }

                    $this.data(pluginName, {
                        target : $this,
                        settings: settings,
                        interval: null,
                        timeout: null,
                        blinking: false
                    });
                }
            });
        },
        start: function(){
            return this.each(function(index){
                var $this = $(this);
                var data = $this.data(pluginName);
                if(!data.blinking){
                    blinkMain.call($this, data);
                    data.interval = setInterval(function(){
                        blinkMain.call($this, data);
                    }, data.settings.cycle);
                    data.blinking = true;
                }
            });
        },
        stop: function(){
            return this.each(function(index){
                var $this = $(this);
                var data = $this.data(pluginName);
                clearInterval(data.interval);
                clearTimeout(data.timeout);
                data.blinking = false;
                $this.css('background-color','');

            });
        }
    };
    // ***** Fin: Public Methods *****
    // *******************************

    // *****************************
    // ***** Start: Supervisor *****
    $.fn[pluginName] = function( method ) {
        if ( methods[method] ) {
            return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || !method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' + method + ' does not exist in jQuery.' + pluginName );
        }
    };
    // ***** Fin: Supervisor *****
    // ***************************
})( jQuery );
