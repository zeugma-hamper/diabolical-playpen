#
# (c) treadle & loam, provisioners llc
#

project ('pen-of-playing', ['cpp', 'c'],
         default_options : ['cpp_std=c++17',
                            'warning_level=2'])

cpp = meson.get_compiler ('cpp')

dl_dep = cpp.find_library('dl')
threads_dep = dependency('threads')

##### boost
boost_dep = dependency ('boost', modules : ['program_options'])

##### OpenImageIO
oiio_dep = dependency ('OpenImageIO')

#### liblo -- you know, for kids! and also for OSC.
lo_dep = dependency ('liblo')

#### libGLU: sure, it's deprecated; but then: aren't we all?
glu_dep = cpp.find_library ('libGLU')


#### the horror. the horror.

zroot = '/opt/trelopro/'

zincpath = zroot + 'include/'
zlibpath = zroot + 'lib'

##### bx
cramps = ['bx', 'bx/3rdparty']
bx_include_dirs = []
foreach q : cramps
  bx_include_dirs += zincpath + q
endforeach


bx_lib = cpp.find_library ('libbx', dirs : zlibpath)

bx_dep = declare_dependency (dependencies : [dl_dep, threads_dep, bx_lib],
                             include_directories : bx_include_dirs)

##### bimg

cramps = ['bimg',
          'bimg/3rdparty',
          'bimg/3rdparty/astc-codec',
          'bimg/3rdparty/astc-codec/include',
          'bimg/3rdparty/iqa/include',
          'bimg/3rdparty/nvtt']
bimg_include_dirs = []
foreach q : cramps
  bimg_include_dirs += zincpath + q
endforeach

bimg_lib = cpp.find_library ('libbimg', dirs : zlibpath)

bimg_dep = declare_dependency (dependencies : [bx_dep, bimg_lib],
                               include_directories : bimg_include_dirs)

##### bgfx

opengl_dep = dependency ('GL')
vulkan_dep = dependency ('Vulkan')
x11_dep = dependency ('x11')

cramps = ['bgfx',
          'bgfx/3rdparty',
          'bgfx/3rdparty/khronos',
                    ]
bgfx_include_dirs = []
foreach q : cramps
  bgfx_include_dirs += zincpath + q
endforeach

bgfx_lib = cpp.find_library ('libbgfx', dirs : zlibpath)

bgfx_dep = declare_dependency (dependencies : [bx_dep, bimg_dep, x11_dep, opengl_dep, vulkan_dep, bgfx_lib],
                               include_directories : bgfx_include_dirs)

#### GLFW

glfw_dep = dependency ('glfw3', required : true)

##### build arguments

gst_dep       = dependency ('gstreamer-1.0')
gst_app_dep   = dependency ('gstreamer-app-1.0')
gst_video_dep = dependency ('gstreamer-video-1.0')

##### ZEUGMA #####

#
# more horror. more horror.
#

shader_prog_gulag = '/opt/trelopro/bin'

# be sure to run `./install_bgfx_tools.sh <BIN_DIR>` before building zeugma
shaderc_prog = find_program ('shaderc', dirs : shader_prog_gulag)

# NOTE: this is only for shaders that turn into headers, most go below
# header_shaders = [
#   ['vertex',   'font/vs_font_basic.sc',
#                'font/varying.def.sc',
#                'vs_font_basic.bin.h'],
#   ['fragment', 'font/fs_font_basic.sc',
#                'font/varying.def.sc',
#                'fs_font_basic.bin.h'],
#   ['vertex',   'font/vs_font_distance_field.sc',
#                'font/varying.def.sc',
#                'vs_font_distance_field.bin.h'],
#   ['fragment', 'font/fs_font_distance_field.sc',
#                'font/varying.def.sc',
#                'fs_font_distance_field.bin.h'],
#   ['vertex',   'font/vs_font_distance_field_subpixel.sc',
#                'font/varying.def.sc',
#                'vs_font_distance_field_subpixel.bin.h'],
#   ['fragment', 'font/fs_font_distance_field_subpixel.sc',
#                'font/varying.def.sc',
#                'fs_font_distance_field_subpixel.bin.h'],
# ]

# header_shader_files = []

# foreach shader : header_shaders
#   fl = custom_target (shader[3],
#                       input : [shader[1], shader[2]],
#                       output : [shader[3]],
#                       command : [shaderc_prog,
#                                  '-i', meson.source_root () + '/bgfx/src',
#                                  '-i', meson.source_root () + '/src/shaders/include',
#                                  '-i', meson.source_root () + '/bgfx/examples',
#                                  '-f', '@INPUT0@',
#                                  '-o', '@OUTPUT0@',
#                                  '--bin2c',
#                                  '--type', shader[0],
#                                  '--varyingdef', '@INPUT1@',
#                                  '--profile', '460'],
#                       build_by_default : true)
#   header_shader_files += [fl]
# endforeach


zeugma_lib = cpp.find_library ('Zeugma', dirs : zlibpath)
zeugma_inc = zincpath + 'zeugma'

zeugma_deps = [bx_dep, bimg_dep, bgfx_dep, glfw_dep,
               gst_dep, gst_app_dep, gst_video_dep,
               oiio_dep, boost_dep, lo_dep,
               zeugma_lib]

toml_inc = include_directories (zincpath + 'toml11')

zeugma_dep = declare_dependency (include_directories : [zeugma_inc, toml_inc,
#                                                       font_inc
                                                       ],
#                                link_with : [zeugma_lib],
                                dependencies : zeugma_deps)



##### SHADERS #####

# # [type, input file, varying file, output file]
 shaders = [
  # the very most basicest
  ['vertex',   'src/shaders/primal_vs.sc',
               'src/shaders/primal_varying.def.sc',
                'primal_vs.bin'],
  ['fragment', 'src/shaders/primal_fs.sc',
               'src/shaders/primal_varying.def.sc',
                'primal_fs.bin'],
  # quad
   ['vertex',   'src/shaders/quad_vs.sc', 'src/shaders/quad_varying.def.sc', 'quad_vs.bin'],
   ['fragment', 'src/shaders/quad_fs.sc', 'src/shaders/quad_varying.def.sc', 'quad_fs.bin'],
  # video
  ['vertex',   'src/shaders/video_vs.sc', 'src/shaders/video_varying.def.sc', 'video_vs.bin'],
  ['fragment', 'src/shaders/video_fs.sc', 'src/shaders/video_varying.def.sc', 'video_fs.bin'],
  # matte video
  ['vertex',   'src/shaders/matte_video_vs.sc',
   'src/shaders/video_varying.def.sc', 'matte_video_vs.bin'],
  ['fragment', 'src/shaders/matte_video_fs.sc',
   'src/shaders/video_varying.def.sc', 'matte_video_fs.bin'],
  # collage background for demo
  ['vertex',   'src/shaders/collage_bg_vs.sc',
   'src/shaders/collage_bg_varying.def.sc', 'collage_bg_vs.bin'],
  ['fragment', 'src/shaders/collage_bg_fs.sc',
   'src/shaders/collage_bg_varying.def.sc', 'collage_bg_fs.bin'],
  # tex-quaddish object
  ['vertex',   'src/shaders/textured_renderable.vs.sc',
   'src/shaders/basic_varying.def.sc', 'textured_renderable.vs.bin'],
  ['fragment', 'src/shaders/textured_renderable.fs.sc',
   'src/shaders/basic_varying.def.sc', 'textured_renderable.fs.bin'],
  # gridularness
  ['vertex',   'src/shaders/grid_vs.sc',
   'src/shaders/grid_varying.def.sc', 'grid_vs.bin'],
  ['fragment', 'src/shaders/grid_fs.sc',
   'src/shaders/grid_varying.def.sc', 'grid_fs.bin'],
  # overlay testing
  ['vertex',   'src/shaders/overlay_vs.sc',
   'src/shaders/overlay_varying.def.sc', 'overlay_vs.bin'],
  ['fragment', 'src/shaders/overlay_fs.sc',
   'src/shaders/overlay_varying.def.sc', 'overlay_fs.bin'],
  # overlay testing
  ['vertex',   'src/shaders/overlay_instance_vs.sc',
   'src/shaders/overlay_varying.def.sc', 'overlay_instance_vs.bin'],
  ['fragment', 'src/shaders/overlay_instance_fs.sc',
   'src/shaders/overlay_varying.def.sc', 'overlay_instance_fs.bin'],
 ]

foreach shader : shaders
  custom_target (shader[3],
                 input : [shader[1], shader[2]],
                 output : [shader[3]],
                 command : [shaderc_prog,
#                            '-i', shader_prog_gulag,
                            '-i', zroot + 'include/bgfx',
#                            '-i', meson.source_root () + '/bgfx/src',
                            '-i', meson.source_root () + '/src/shaders/include',
                            '-f', '@INPUT0@',
                            '-o', '@OUTPUT0@',
                            '--type', shader[0],
                            '--varyingdef', '@INPUT1@',
                            '--profile', '460'],
                 build_by_default : true)
endforeach



##### EXECUTABLES #####

tample_exec = executable ('playpen',
                           sources : ['src/Cursoresque.cpp',
                                      'src/playpen.cpp'
                                     ],
                           dependencies : [zeugma_dep, glu_dep],
                           include_directories : zincpath)
