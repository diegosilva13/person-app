$(function() {
    $('#side-menu').metisMenu();
});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        var topOffset = 50;
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    // var element = $('ul.nav a').filter(function() {
    //     return this.href == url;
    // }).addClass('active').parent().parent().addClass('in').parent();
    var element = $('ul.nav a').filter(function() {
        return this.href == url;
    }).addClass('active').parent();

    while (true) {
        if (element.is('li')) {
            element = element.parent().addClass('in').parent();
        } else {
            break;
        }
    }
});

$('#add_more').click(function() {
  "use strict";
  $(this).before($("<div/>", {
    id: 'filediv'
  }).fadeIn('slow').append(
    $("<input/>", {
      name: 'file[]',
      type: 'file',
      id: 'file',
      multiple: 'multiple',
      accept: 'image/*'
    })
  ));
});

$('#upload').click(function(e) {
  "use strict";
  e.preventDefault();

  if (window.filesToUpload.length === 0 || typeof window.filesToUpload === "undefined") {
    alert("No files are selected.");
    return false;
  }

  // Now, upload the files below...
  // https://developer.mozilla.org/en-US/docs/Using_files_from_web_applications#Handling_the_upload_process_for_a_file.2C_asynchronously
});

deletePreview = function (ele, i) {
  "use strict";
  try {
    $(ele).parent().remove();
    window.filesToUpload.splice(i, 1);
  } catch (e) {
    console.log(e.message);
  }
}

$("#file").on('change', function() {
  "use strict";
  // create an empty array for the files to reside.
  window.filesToUpload = [];

  if (this.files.length >= 1) {
    $("[id^=previewImg]").remove();
    $.each(this.files, function(i, img) {
      var reader = new FileReader(),
        newElement = $("<div id='previewImg" + i + "' class='previewBox'><img /></div>"),
        deleteBtn = $("<span class='delete' onClick='deletePreview(this, " + i + ")'>X</span>").prependTo(newElement),
        preview = newElement.find("img");

      reader.onloadend = function() {
        preview.attr("src", reader.result);
        preview.attr("alt", img.name);
      };

      try {
        window.filesToUpload.push(document.getElementById("file").files[i]);
      } catch (e) {
        console.log(e.message);
      }

      if (img) {
        reader.readAsDataURL(img);
      } else {
        preview.src = "";
      }

      newElement.appendTo("#filediv");
    });
  }
});
