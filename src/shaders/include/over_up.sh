#ifndef OVER_UP_POINTS_THE_WAYS
#define OVER_UP_POINTS_THE_WAYS

vec2 calculate_dimensions (vec4 _over, vec4 _up)
{
  return vec2 (length (_over.xyz), length (_up.xyz));
}

vec4 calculate_norm (vec4 _over, vec4 _up)
{
  return vec4 (cross (_over.xyz, _up.xyz), 0.0);
}

mat4 make_rot_matrix (vec4 _over, vec4 _up, vec4 _norm)
{
  return mat4 (_over, _up, _norm, vec4 (0.0, 0.0, 0.0, 1.0));
}

vec4 calculate_inv_aspect_ratio (float _width, float _height)
{
  if (_height > _width)
    return vec4 (_width / _height, 1.0, 1.0, 1.0);

  return vec4 (1.0, _height / _width, 1.0, 1.0);
}

#endif //OVER_UP_POINTS_THE_WAYS
