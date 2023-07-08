package com.xiaoyao.hxds.bff.customer.service;

import com.xiaoyao.hxds.bff.customer.controller.form.CarAddForm;
import com.xiaoyao.hxds.bff.customer.controller.form.CarDeleteForm;

import java.util.Map;

public interface CarService {
    Map<String, Object> addCar(CarAddForm form);

    Map<String, Object> deleteCar(CarDeleteForm form);

    Map<String, Object> listCar();
}
