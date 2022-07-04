import 'dart:async';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:lab8/utilities/showWorker.dart';

import '../data/worker.dart';
import '../data/workers.dart';

class VisualizationPage extends StatefulWidget {
  const VisualizationPage({Key? key}) : super(key: key);

  @override
  State<VisualizationPage> createState() => _VisualizationPageState();
}

class _VisualizationPageState extends State<VisualizationPage> {
  late List<Worker> workers;
  late int maxElements;
  List<double> _widths = [];
  List<double> _heights = [];
  List<Color> _colors = [];
  List<BorderRadiusGeometry> _borderRadiuses = [];

  late Timer _timer;

  @override
  void initState() {
    super.initState();

    this.workers = List.of(allWorkers);
    maxElements = workers.length;

    for(int i = 0; i < maxElements; i++) {
      final random = Random();
      _widths.add((random.nextInt(50) + 30).toDouble());
      _heights.add((random.nextInt(50) + 30).toDouble());
      _colors.add(Color.fromRGBO(
        workers[i].salary.hashCode % 256,
        workers[i].coordinates.x.hashCode % 256,
        workers[i].coordinates.y.hashCode % 256,
        1,
      ));
      _borderRadiuses.add(BorderRadius.circular(random.nextInt(100).toDouble()));

    }

    _timer = Timer.periodic(const Duration(seconds: 1), (Timer t) {
      setState(() {
        final random = Random();

        for(int i = 0; i < maxElements; i++) {
          _widths[i] = (random.nextInt(80) + 50).toDouble();
          _heights[i] = (random.nextInt(80) + 50).toDouble();
          _colors[i] = Color.fromRGBO(
            workers[i].salary.hashCode % 256,
            workers[i].coordinates.x.hashCode % 256,
            workers[i].coordinates.y.hashCode % 256,
            1,
          );
          _borderRadiuses[i] =
              BorderRadius.circular(random.nextInt(100).toDouble());
        }
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return GridView.builder(
        itemCount: maxElements,
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount:3),
        itemBuilder: (context, i) {
          return InkResponse(
            onTap: () => _onWorkerClicked(context, i),
            child: Center(
              child: AnimatedContainer(
                // Use the properties stored in the State class.
                width: _widths[i],
                height: _heights[i],
                decoration: BoxDecoration(
                  color: _colors[i],
                  borderRadius: _borderRadiuses[i],
                ),
                // Define how long the animation should take.
                duration: const Duration(seconds: 1),
                // Provide an optional curve to make the animation feel smoother.
                curve: Curves.fastOutSlowIn,
              ),
            ),
          );
        }
    );

  }

  Future _onWorkerClicked(BuildContext context, int index) async {
    await showWorkerDialog(
        context,
        worker: workers[index],
    );
  }
}
