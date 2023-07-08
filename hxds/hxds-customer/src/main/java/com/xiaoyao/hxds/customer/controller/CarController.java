package com.xiaoyao.hxds.customer.controller;

import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.customer.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/car")
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    public R addCar(@RequestBody Map<String, Object> param) {
        return R.ok(carService.addCar(param));
    }

    @PostMapping("/delete")
    public R deleteCar(@RequestBody Map<String, Object> param) {
        return R.ok(carService.deleteCar(param));
    }

    @PostMapping("/list")
    public R listCars(@RequestBody Map<String, Object> param) {
        return R.ok(carService.listCars(param));
    }
}
