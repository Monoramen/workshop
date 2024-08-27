function openEditOrderModal(button) {
    var orderId = button.getAttribute('data-order-id');
    $.get("/modals/edit-order-modal?id=" + encodeURIComponent(orderId), function (data) {
        $('#editOrderModal').find('.modal-body').html(data);
        $('#editOrderModal').modal('show');
    });
}


function openAddOrderModal() {
    $.get("/modals/order-form", function (data) {
        $('#createOrderModal').find('.modal-title').html('Создание заказа');
        $('#createOrderModal').find('#mdlFooterBlock').remove();
        $('#createOrderModal').find('.modal-body').html(data);


        $('#createOrderModal').modal('show');
    });
}

function saveChanges() {
    var form = $('#editOrderForm'); // Получаем форму
    var orderId = form.find('input[name="id"]').val(); // Получаем ID заказа

    // Получаем CSRF токен и заголовок из мета-тегов
    var csrfToken = $('#_csrf').attr("content");
    var csrfHeader = $('#_csrf_header').attr("content");

    // Отправляем форму как обычные параметры формы
    $.ajax({
        url: '/api/order/' + encodeURIComponent(orderId),
        type: 'POST',
        data: form.serialize(), // Используем serialize для отправки данных формы
        beforeSend: function(xhr) {
            // Устанавливаем CSRF заголовок
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            // Обработка успешного сохранения
            // Удаляем модальное окно из DOM
            $('#editOrderModal').modal('hide');

            var row = $('.table tr[data-id="' + response.id + '"]');
            row.find('td:nth-child(2)').text(response.client.name + ' ' + response.client.surname);
            row.find('td:nth-child(3)').text(response.client.phone);
            row.find('td:nth-child(4)').text(response.service.name);
            row.find('td:nth-child(5)').text(response.date);


        },
        error: function(jqXHR, textStatus, errorThrown) {
            // Обработка ошибки
            console.log("ERROR: " + textStatus, errorThrown);
        }
    });
}
$("#saveChangesBtn").click(function() {
    saveChanges();


});

$(document).ready(function() {
    var csrfToken = $('#_csrf').attr("content");
    var csrfHeader = $('#_csrf_header').attr("content");

    console.log("CSRF Token: " + csrfToken);
    console.log("CSRF Header: " + csrfHeader);
});


$('.table .delBtn').on('click', function(event) {
    event.preventDefault();
    var orderId = $(this).data('id');
    console.log(orderId);
    $('#deleteOrderModal #delRef').data('id', orderId);
    $('#deleteOrderModal').modal('show');
});

$('#delRef').on('click', function(event) {
    event.preventDefault();
    var orderId = $(this).data('id');
    var csrfToken = $('#_csrf').attr("content");
    var csrfHeader = $('#_csrf_header').attr("content");
    $.ajax({
        url: 'api/order/delete/' + orderId,
        type: 'DELETE',
        beforeSend: function(xhr) {
            // Устанавливаем CSRF заголовок
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            // Удаление строки таблицы с удаленным заказом
            console.log(response.id);
            $('.table tr[data-id="' + orderId + '"]').remove();
            // Закрытие модального окна
            $('#deleteOrderModal').modal('hide');
        },
        error: function (error) {
            console.log(error);
        }
    });
});

$(document).ready(function() {
    $('#clientSelect').change(function() {
        if ($(this).val() === "-1") {
            $('#newClientForm').show();
        } else {
            $('#newClientForm').hide();
        }
    });
});