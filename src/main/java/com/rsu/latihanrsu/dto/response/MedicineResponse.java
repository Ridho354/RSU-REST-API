package com.rsu.latihanrsu.dto.response;

import java.util.List;
import com.rsu.latihanrsu.entity.MedicineImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineResponse {
  
  private String id;
  private String name;
  private Long price;
  private String info;
  List<MedicineImage> images;
}