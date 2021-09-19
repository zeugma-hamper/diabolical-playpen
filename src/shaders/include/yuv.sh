#ifndef YUV_2_RGB
#define YUV_2_RGB


vec3 convert_bt601_scaled (vec3 _yuv)
{
  vec3 sub_factor = vec3 (16.0/255.0, 0.5, 0.5);

  vec3 y_col = vec3 (1.1643835616438356, 1.1643835616438356, 1.1643835616438356);
  vec3 u_col = vec3 (0.0, -0.39176229009491365, 2.017232142857143);
  vec3 v_col = vec3 (1.5960267857142858, -0.8129676472377709, 0.0);
  mat3 conv_factor = mat3 (y_col, u_col, v_col);

  return mul (conv_factor, _yuv - sub_factor);
}

#endif //YUV_2_RGB
