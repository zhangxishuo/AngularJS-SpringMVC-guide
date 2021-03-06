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
  .controller('KlassEditCtrl', function($scope, $http, $stateParams, $state, teacher) {
    var self = this;
    self.init = function() {
      // 应该获取当前这个班级
      var id = $stateParams.id;
      var url = '/Klass/' + id;
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
      var url = '/Klass/' + id;
      $http.put(url, $scope.data)
        .then(function success(response) {
          // 把获取的班级，传给V层（V层进行绑定)
          $state.transitionTo('klass', {}, { reload: true });
        }, function error(response) {
          console.log('请求失败 ' + url, response);
        });
    };


    self.init();
    $scope.submit = self.submit;
  });
