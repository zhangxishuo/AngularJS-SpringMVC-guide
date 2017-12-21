'use strict';

/**
 * @ngdoc function
 * @name webAppApp.controller:KlassEditCtrl
 * @description
 * # 班级管理 编辑
 * Controller of the webAppApp
 * @author panjie
 */
angular.module('webAppApp')
  .controller('KlassEditCtrl', function($scope, $http, $stateParams) {
    var self = this;
    self.init = function() {
      // 应该获取当前这个班级
      var id = $stateParams.id;
      var url = 'http://127.0.0.1:8080/Klass/' + id;
      $http.get(url)
        .then(function success(response) {
          // 把获取的班级，传给V层（V层进行绑定)
          $scope.data = response.data;
        }, function error(response) {
          console.log('请求失败 ' + url, response);
        });
    };

    // 提交数据
    self.submit = function() {
      var id = $stateParams.id;
      var url = 'http://127.0.0.1:8080/Klass/' + id;
      $http.put(url)
        .then(function success(response) {
          // 把获取的班级，传给V层（V层进行绑定)
          console.log('更新成功');
        }, function error(response) {
          console.log('请求失败 ' + url, response);
        });
    };


    self.init();
    $scope.submit = self.submit;
  });
